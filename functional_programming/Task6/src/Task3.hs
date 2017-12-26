module Task3 where

import           Control.Monad.Trans.State

moving :: Int -> [Double] -> [Double]
moving n xs = evalState (mapM evaluate xs) []
  where
    evaluate :: Double -> State [Double] Double
    evaluate x = get >>= \s -> 
                     let pr = s ++ [x] in put (dropK n pr)
                     >> gets (\v -> 
                     let l = fromIntegral $ length v :: Double in
                     if length pr <= n then
                     sum pr / l
                     else sum v / l)
    dropK k l = drop (length l - k) l
