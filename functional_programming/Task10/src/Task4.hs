{-# LANGUAGE TemplateHaskell #-}
module Task4 where

import           Data.Function       (fix)
import           Language.Haskell.TH

fib :: [Integer]
fib = 0 : 1 : zipWith (+) fib (tail fib)

fibQ :: Int -> Q Exp
fibQ n = [| fib !! n |]
