module Main where

import           Criterion.Main
import qualified Data.DList     as DL
import qualified Data.Foldable  as F
import qualified Data.Sequence  as DS
import qualified Task1          as M

l :: [Int]
l = [1,2..500]

main :: IO()
main = defaultMain
  [ bgroup "concat500"
    [ bench "task" $ nf (M.concat l) l
    , bench "dlist" $ nf DL.toList (DL.append (DL.fromList l) (DL.fromList l))
    , bench "seq" $ nf F.toList (DS.fromList l DS.>< DS.fromList l)
    ]
  ]
