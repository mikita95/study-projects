module Task1
       ( InNode (..)
       , leastCommonAncestor
       , add
       , depth
       , test ) where

import           Data.Maybe (isNothing)

data InNode a = Node
    { label  :: a
    , parent :: Maybe (InNode a)
    } deriving (Show, Eq)

add :: a -> InNode a -> InNode a
add a t = Node a (Just t)

depth :: InNode a -> Int
depth a = maybe 0 depth (parent a) + 1

leastCommonAncestor :: Eq a => InNode a -> InNode a -> Maybe (InNode a)
leastCommonAncestor a b = help a b (depth a) (depth b)
  where
    help f s df ds
        | df < ds   = ps >>= (\x -> help f x df (ds - 1))
        | df > ds   = pf >>= (\x -> help x s (df - 1) ds)
        | otherwise = pf >>= (\x -> ps >>= (\y -> if label x == label y
                                                  then return x
                                                  else leastCommonAncestor x y))
      where
        pf = parent f
        ps = parent s


node8 :: InNode Int -- 8 -> 4 -> 5 -> 2 -> 3 -> 9
node8 = add 8 node4

node4 :: InNode Int -- 4 -> 5 -> 2 -> 3 -> 9
node4 = add 4 node5

node5 :: InNode Int -- 5 -> 2 -> 3 -> 9
node5 = add 5 $ add 2 $ add 3 $ Node 9 Nothing

node7 :: InNode Int -- 7 -> 6 -> 5 -> 2 -> 3 -> 9
node7 = add 7 $ add 6 node5

node0 :: InNode Int -- 0 -> 10 -> 11 -> 12
node0 = add 0 $ add 10 $ add 11 $ Node 12 Nothing

tests :: [Bool]
tests = [ leastCommonAncestor node8 node7 == Just node5
        , leastCommonAncestor node8 node4 == Just node5
        , leastCommonAncestor node4 node8 == Just node5
        , leastCommonAncestor node4 node7 == Just node5
        , isNothing $ leastCommonAncestor node0 node7
        , isNothing $ leastCommonAncestor node5 node0
        , leastCommonAncestor node7 node8 == Just node5
        ]

test :: Bool
test = and tests
