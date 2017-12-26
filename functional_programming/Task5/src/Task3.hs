module Task3 where

import           Control.Applicative (Alternative (..))
import           Data.Char           (isAlpha, isAlphaNum, isSpace)
import           Task2

zeroOrMore :: Parser a -> Parser [a]
zeroOrMore p = oneOrMore p <|> pure []

oneOrMore :: Parser a -> Parser [a]
oneOrMore p = (:) <$> p <*> zeroOrMore p

spaces :: Parser String
spaces = zeroOrMore (satisfy isSpace)

type Ident = String

data Atom = N Integer
            | I Ident
            deriving (Show)

data SExpr = A Atom
             | Comb [SExpr]
             deriving (Show)

ident :: Parser String
ident = (:) <$> satisfy isAlpha <*> zeroOrMore (satisfy isAlphaNum)

atom :: Parser Atom
atom = N <$> posInt <|> I <$> ident

comb :: Parser [SExpr]
comb = parents $ zeroOrMore parseSExpr
  where
    parents x = char '(' *> x <* char ')'

parseSExpr :: Parser SExpr
parseSExpr = spaces2 $ A <$> atom <|> Comb <$> comb
  where
    spaces2 x = spaces *> x <* spaces
