module Task1 where

import           Language.Haskell.TH

selN :: Int -> Int -> Q Exp
selN n k = do
    x <- newName "x"
    return $ LamE [TupP $ replicate (k - 1) WildP ++ VarP x : replicate (n - k) WildP] $ VarE x
