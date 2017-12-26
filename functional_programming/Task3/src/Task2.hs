{-# LANGUAGE MultiWayIf #-}

module Task2
       ( BST (..)
       , mempty
       , empty
       , singleton
       , isLeaf
       , isNode
       , key
       , find
       , insert
       , delete
       , toList
       , fromList
       , printTree
       , next ) where

import           Data.List   (take, unfoldr, zip)
import           Data.Maybe  (fromJust, fromMaybe, isNothing)
import           Data.Monoid ((<>))
import qualified TreePrinter as TP (Tree (Leaf, Node), verticalPrint)

data BST k = Leaf
             | Node k (BST k) (BST k)
             deriving (Show, Eq)

instance (Ord k) => Monoid (BST k) where
    mempty = Leaf
    mappend Leaf n = n
    mappend n Leaf = n
    mappend a b = fromList (merge (toList a) (toList b))
      where
        merge n [] = n
        merge [] n = n
        merge (x:xs) (y:ys)
            | x < y = x : merge xs (y : ys)
            | otherwise = y : merge (x : xs) ys


instance Foldable BST where
    foldMap f Leaf         = mempty
    foldMap f (Node k l r) = foldMap f l <> f k <> foldMap f r

empty :: Ord k => (BST k)
empty = mempty

singleton :: Ord k => k -> BST k
singleton x = Node x Leaf Leaf

isLeaf :: Ord k => BST k -> Bool
isLeaf Leaf = True
isLeaf _    = False

isNode :: Ord k => BST k -> Bool
isNode Leaf = False
isNode _    = True

key :: Ord k => BST k -> Maybe k
key (Node nk _ _ ) = Just nk
key Leaf           = Nothing

bstToTree :: BST k -> TP.Tree k
bstToTree Leaf            = TP.Leaf
bstToTree (Node nk nl nr) = TP.Node nk (bstToTree nl) (bstToTree nr)

findNode :: Ord k => k -> BST k -> Maybe (BST k)
findNode _ Leaf = Nothing
findNode k n@(Node nk nl nr)
    | k < nk    = findNode k nl
    | k > nk    = findNode k nr
    | otherwise = Just n

find :: Ord k => k -> BST k -> Maybe k
find k n = maybe Nothing key (findNode k n)

insert :: Ord k => k -> BST k -> BST k
insert k Leaf = Node k Leaf Leaf
insert k n@(Node nk nl nr)
    | k < nk    = Node nk (insert k nl) nr
    | k > nk    = Node nk nl (insert k nr)
    | otherwise = n

findMin :: Ord k => BST k -> Maybe (BST k)
findMin Leaf = Nothing
findMin n@(Node nk nl nr)
    | isLeaf nl = Just n
    | otherwise  = Just (fromMaybe nl $ findMin nl)

findMax :: Ord k => BST k -> Maybe (BST k)
findMax Leaf = Nothing
findMax n@(Node nk nl nr)
    | isLeaf nr = Just n
    | otherwise  = Just (fromMaybe nr $ findMax nr)

delete :: Ord k => k -> BST k -> BST k
delete k Leaf = Leaf
delete k (Node nk nl nr)
    | k < nk    = Node nk (delete k nl) nr
    | k > nk    = Node nk nl (delete k nr)
    | isNode nl && isNode nr = Node (fromMaybe nk $ key m)
                                    nl
                                    (delete (fromMaybe nk $ key nr) nr)
    | isNode nl = nl
    | otherwise = nr
  where m = fromMaybe Leaf $ findMin nr

toList :: Ord k => BST k -> [k]
toList Leaf            = []
toList (Node nk nl nr) = toList nl ++ [nk] ++ toList nr

fromList :: Ord k => [k] -> BST k
fromList = foldr insert Leaf

next :: Ord k => k -> BST k -> Maybe k
next k n
    | isNothing nn = Nothing
    | otherwise = key (fromJust nn)
  where nn = nextNode k n Leaf

nextNode :: Ord k => k -> BST k -> BST k -> Maybe (BST k)
nextNode _ Leaf _ = Nothing
nextNode k root@(Node nk nl nr) s
    | fromMaybe k (key root) == k = if | isNode nr -> findMin nr
                                       | otherwise -> if isLeaf s
                                                      then Nothing
                                                      else Just s
    | k < nk                      = nextNode k nl root
    | otherwise                   = nextNode k nr s

printTree :: Show k => BST k -> IO()
printTree t = putStr $ TP.verticalPrint (bstToTree t)
