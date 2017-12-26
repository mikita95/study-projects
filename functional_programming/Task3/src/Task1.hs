module Task1
       ( Coin (..)
       , blue
       , red
       , yellow
       , createCoins
       , compareCoins
       ) where

import           Data.Monoid ((<>))

newtype Coin color = Coin
    { getCoin :: Int
    } deriving (Show)

data Blue
data Yellow
data Red

blue = undefined :: Blue
red  = undefined :: Red
yellow = undefined :: Yellow

createCoins :: color -> Int -> Coin color
createCoins _ = Coin

c1 = createCoins blue 5
c2 = createCoins red 10
c3 = mempty :: Coin Yellow
c4 = createCoins yellow 7

instance Num (Coin color) where
    (Coin a) + (Coin b) = Coin (a + b)
    (Coin a) * (Coin b) = Coin (a * b)
    abs (Coin a)        = Coin (abs a)
    negate (Coin a)     = Coin (negate a)
    signum (Coin a)     = Coin (signum a)
    fromInteger n       = Coin (fromInteger n)

instance Monoid (Coin color) where
    mempty      = 0
    mappend x y = x + y

class Color a where
    priority :: a -> Int

instance Color Red where
    priority c = 0

instance Color Blue where
    priority c = 1

instance Color Yellow where
    priority c = 2

color :: Coin color -> color
color coin = undefined

compareCoins :: (Color c1, Color c2) => Coin c1 -> Coin c2 -> Ordering
compareCoins f@(Coin a) s@(Coin b) = compare (priority $ color f) (priority $ color s) <> compare a b
