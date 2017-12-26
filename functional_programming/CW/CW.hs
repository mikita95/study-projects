module CW where

import           Data.Monoid

-- find 2^k by n : 2^k <= n, n >= 1

binPow :: Integer -> Integer
binPow n = 2 ^ (floor . logBase 2.0 . fromIntegral) n

testBinPow1 = binPow 1 <= 1
testBinPow2 = binPow 5 <= 5
testBinPow3 = binPow 20 <= 20

-- find C(n,k)

c :: Integer -> Integer -> Integer
c n k = evaluate 1 1 n k
  where
    evaluate l r _ 0 = l `div` r
    evaluate _ _ 0 _ = 0
    evaluate l r n k = evaluate (l * n) (r * k) (n - 1) (k - 1)

testC1 = c 52 5 == 2598960
testC2 = c 2 0 == 1
testC3 = c 3 2 == 3
testC4 = c 2 3 == 0

-- Implement Deque on two lists

data Deque a = Deque [a] [a]
    deriving (Show, Eq)

emptyDeque :: Deque a
emptyDeque = Deque [] []

pushToHead :: a -> Deque a -> Deque a
pushToHead v (Deque l r) = Deque (v:l) r

pushToTail :: a -> Deque a -> Deque a
pushToTail v (Deque l r) = Deque l (v:r)

getHead :: Deque a -> Maybe a
getHead (Deque [] [])     = Nothing
getHead (Deque x@(v:l) r) = Just v
getHead (Deque [] r)      = Just (last r)

getTail :: Deque a -> Maybe a
getTail (Deque [] [])     = Nothing
getTail (Deque l x@(v:r)) = Just v
getTail (Deque l [])      = Just (head l)

popFromHead :: Deque a -> Deque a
popFromHead (Deque [] r)      = Deque [] (init r)
popFromHead (Deque x@(v:l) r) = Deque l r

popFromTail :: Deque a -> Deque a
popFromTail (Deque l [])      = Deque (init l) []
popFromTail (Deque l x@(v:r)) = Deque l r

d = emptyDeque :: Deque Int
testDeque1 = getTail (pushToHead 1 d) == Just 1
testDeque2 = getHead (pushToTail 4 $ pushToHead 3 $ pushToTail 2 $ pushToHead 1 d)
             == Just 3

-- Implement Monoid for Deque

instance Monoid (Deque a) where
    mempty = emptyDeque
    mappend (Deque l1 r1) (Deque l2 r2) = Deque (l1 ++ l2) (r1 ++ r2)
    
m = mempty :: (Deque Int)
testMonoid1 = getHead
              (pushToHead 1 (pushToTail 2 m) <> pushToHead 3 (pushToHead 4 m))
              == Just 1

