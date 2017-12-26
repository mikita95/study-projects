{-# LANGUAGE OverloadedStrings #-}

module Task2 where

import           Control.Applicative        (empty, optional, (<|>))
import           Control.Monad              (liftM2, void)
import           Control.Monad.Trans.Reader
import qualified Data.Map                   as M
import           Data.Maybe                 (fromJust)
import qualified Data.Text                  as T
import qualified Options.Applicative        as OA
import qualified Text.Megaparsec            as TM
import qualified Text.Megaparsec.Expr       as TME
import qualified Text.Megaparsec.Lexer      as L
import qualified Text.Megaparsec.Text       as TMT

data AExpr = Neg AExpr
           | ABin ABinOp AExpr AExpr
           | Lit Integer
           | Var T.Text
           deriving (Show)

data ABinOp = Add
            | Sub
            | Mul
            | Div
            | Pow
            deriving (Show)

sc :: TMT.Parser ()
sc = L.space (void TM.spaceChar) empty empty

lexeme :: TMT.Parser a -> TMT.Parser a
lexeme = L.lexeme sc

symbol :: T.Text -> TMT.Parser T.Text
symbol s = T.pack <$> L.symbol sc (T.unpack s)

parens :: TMT.Parser a -> TMT.Parser a
parens = TM.between (symbol "(") (symbol ")")

integer :: TMT.Parser Integer
integer = lexeme L.integer

aExpr :: TMT.Parser AExpr
aExpr = TM.many TM.spaceChar *> TME.makeExprParser aTerm aOperators

aOperators :: [[TME.Operator TMT.Parser AExpr]]
aOperators =
  [ [ TME.Prefix (symbol "-" *> pure Neg) ]
  , [ TME.InfixL (symbol "*" *> pure (ABin Mul))
    , TME.InfixL (symbol "/" *> pure (ABin Div))
    , TME.InfixL (symbol "^" *> pure (ABin Pow)) ]
  , [ TME.InfixL (symbol "+" *> pure (ABin Add))
    , TME.InfixL (symbol "-" *> pure (ABin Sub)) ]
  ]

identifier :: TMT.Parser T.Text
identifier = T.pack <$> (lexeme . TM.try) p
  where
    p = (:) <$> TM.letterChar <*> TM.many TM.alphaNumChar

aTerm :: TMT.Parser AExpr
aTerm = parens aExpr       TM.<|>
        Var <$> identifier TM.<|>
        Lit <$> integer

aPair :: TMT.Parser (T.Text, Integer)
aPair = TM.try p
  where
    p = (,) <$ TM.many TM.spaceChar <* symbol "(" <*>
               identifier           <* symbol "," <*>
               integer              <* symbol ")"

parsePairs :: T.Text -> M.Map T.Text Integer
parsePairs s = M.fromList $
               either (\_ -> error "Error of vals map parsing")
                      id
                      (TM.parse aPairs "<stdin>" s)

aPairs :: TMT.Parser [(T.Text, Integer)]
aPairs = TM.sepBy1 aPair (TM.char ',')

eval :: AExpr -> Reader (M.Map T.Text Integer) (Maybe Integer)
eval (Neg a)        = eval a >>= \x -> return (fmap negate x)
eval (ABin Add a b) = evalBinOp a b (+)
eval (ABin Sub a b) = evalBinOp a b (-)
eval (ABin Mul a b) = evalBinOp a b (*)
eval (ABin Div a b) = evalBinOp a b div
eval (ABin Pow a b) = evalBinOp a b (^)
eval (Lit a)        = return $ Just a
eval (Var v)        = ask >>= \x -> return (look x)
  where look y = M.lookup v y <|>
                 error ("Evaluation error: variable " ++ show v ++ " is not defined")

evalBinOp :: AExpr -> AExpr ->
             (Integer -> Integer -> Integer) ->
             Reader (M.Map T.Text Integer) (Maybe Integer)
evalBinOp a b f = eval a
                  >>= \x -> eval b
                  >>= \y -> return (liftM2 f x y)

vs :: M.Map T.Text Integer
vs = M.singleton "x" 1

parseExpr ::  T.Text -> AExpr
parseExpr s = either (\_ -> error "Parse error") id (TM.parse aExpr "<stdin>" s)

type VarMap     = T.Text
type ExprString = T.Text
data Command    = Eval | Print deriving (Show)
data Options    = Options Command ExprString (Maybe VarMap)

main' :: IO ()
main' = run =<< OA.execParser
    (parseOptions `withInfo` "Arithmetic calculator")

run :: Options -> IO ()
run (Options cmd es vm) =
    case cmd of
        Eval  -> putStr $ show
                (fromJust
                 (runReader
                 (eval $ parseExpr es)
                 (maybe M.empty parsePairs vm)))
        Print -> putStr $ show (parseExpr es)


withInfo :: OA.Parser a -> T.Text -> OA.ParserInfo a
withInfo opts desc = OA.info (OA.helper <*> opts ) $ OA.progDesc (T.unpack desc)

parseOptions :: OA.Parser Options
parseOptions = Options <$> parseCommand <*> parseExprString <*> parseVarMap

parseExprString :: OA.Parser ExprString
parseExprString = T.pack <$> OA.strOption (
    OA.short 'e'      OA.<>
    OA.long "expr"    OA.<>
    OA.metavar "EXPR" OA.<>
    OA.help "Expression to deal with")

parseVarMap :: OA.Parser (Maybe VarMap)
parseVarMap = optional (T.pack <$> OA.strOption (
    OA.short 'v'     OA.<>
    OA.long "vars"   OA.<>
    OA.metavar "MAP" OA.<> OA.help "Map for variables"))

parseEval :: OA.Parser Command
parseEval = pure Eval

parsePrint :: OA.Parser Command
parsePrint = pure Print

parseCommand :: OA.Parser Command
parseCommand = OA.subparser $
    OA.command "eval" (parseEval `withInfo` "Eval an expression") OA.<>
    OA.command "print-asc" (parsePrint `withInfo` "Print parsed expression")
