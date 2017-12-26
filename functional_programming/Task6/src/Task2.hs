module Task2 where

import           Control.Monad              (liftM2)
import           Control.Monad.Trans.Reader
import           Data.Map                   as M

data Expr = Add Expr Expr
          | Mul Expr Expr
          | Lit Int
          | Var String
          | Assign String Int Expr
          deriving (Show)

eval :: Expr -> Reader (Map String Int) (Maybe Int)
eval (Add a b) = eval a
                 >>= \x -> eval b
                 >>= \y -> return (liftM2 (+) x y)
eval (Mul a b) = eval a
                 >>= \x -> eval b
                 >>= \y -> return (liftM2 (*) x y)
eval (Lit a)   = return $ Just a
eval (Var v)   = ask >>= \x -> return (M.lookup v x)
eval (Assign s n e) = local (insert s n) (eval e)

vs :: Map String Int
vs = singleton "x" 1

