{-# LANGUAGE MultiWayIf #-}

module Task1
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
       , printTree) where

import           Control.Monad.Trans.Writer.Lazy
import           Data.Maybe                      (fromMaybe)
import qualified TreePrinter                     as TP (Tree (Leaf, Node), verticalPrint)

data BST k = Leaf
             | Node k (BST k) (BST k)
             deriving (Show, Eq)

--writerValue :: Writer a b -> b
--writerValue x = fst $ runWriter x

empty :: BST k
empty = Leaf

singleton :: k -> BST k
singleton x = Node x Leaf Leaf

isLeaf :: BST k -> Bool
isLeaf Leaf = True
isLeaf _    = False

isNode :: BST k -> Bool
isNode Leaf = False
isNode _    = True

key :: BST k -> Maybe k
key (Node nk _ _ ) = Just nk
key Leaf           = Nothing

bstToTree :: BST k -> TP.Tree k
bstToTree Leaf            = TP.Leaf
bstToTree (Node nk nl nr) = TP.Node nk (bstToTree nl) (bstToTree nr)

findNode :: (Show k, Ord k) => k -> BST k -> Writer String (Maybe (BST k))
findNode _ Leaf = writer (Nothing, "there is no such value\n")
findNode k n@(Node nk nl nr)
    | k < nk    = findNode k nl >>= \b -> writer (b,
                  "value " ++ show k ++ " < node value " ++ show nk ++ ", so go left\n")
    | k > nk    = findNode k nr >>= \b -> writer (b,
                  "value " ++ show k ++ " > node value " ++ show nk ++ ", so go right\n")
    | otherwise = writer (Just n, "node has been found\n")

find :: (Show k, Ord k) => k -> BST k -> Writer String (Maybe k)
find k n = findNode k n >>= \b -> writer (maybe Nothing key b, "try to find value\n")

insert :: (Show k, Ord k) => k -> BST k -> Writer String (BST k)
insert k Leaf = writer (Node k Leaf Leaf, "insert value " ++ show k ++ "\n")
insert k n@(Node nk nl nr)
    | k < nk    = insert k nl >>= \b -> writer (Node nk b nr,
                  "value " ++ show k ++ " < " ++ "node value " ++ show nk ++ ", so go left\n")
    | k > nk    = insert k nr >>= \b -> writer (Node nk nl b,
                  "value " ++ show k ++ " > " ++ "node value " ++ show nk ++ ", so go right\n")
    | otherwise = writer (n, "value has been already added\n")

findMin :: (Show k, Ord k) => BST k -> Writer String (Maybe (BST k))
findMin Leaf = writer (Nothing, "there is no min value\n")
findMin n@(Node nk nl _)
    | isLeaf nl = writer (Just n, "min value " ++ show nk ++ " has been found\n")
    | otherwise  = findMin nl >>= \b -> writer (Just (fromMaybe nl b), "go to the left child\n")

delete :: (Show k, Ord k) => k -> BST k -> Writer String (BST k)
delete _ Leaf = writer (Leaf, "there is nothing to delete")
delete k (Node nk nl nr)
    | k < nk    = delete k nl >>= \b ->
                  writer (Node nk b nr,
                  "value " ++ show k ++ " < node value " ++ show nk ++ ", so go left\n")
    | k > nk    = delete k nr >>= \b ->
                  writer (Node nk nl b,
                  "value " ++ show k ++ " > node value " ++ show nk ++ ", so go left\n")
    | isNode nl && isNode nr = delete (fromMaybe nk $ key nr) nr
                               >>= \a ->
                               findMin nr
                               >>= \b ->
                               writer (fromMaybe Leaf b, "making min node of subtree\n")
                               >>= \c ->
                               writer (Node (fromMaybe nk $ key c) nl a, "min has benn found\n")
    | isNode nl = writer (nl, "return left child\n")
    | otherwise = writer (nr, "return right child\n")

toList :: (Show k, Ord k) => BST k -> Writer String [k]
toList Leaf            = writer ([], "empty subtree\n")
toList (Node nk nl nr) = toList nl
                         >>= \a ->
                         writer (a ++ [nk], "go left and join with node value " ++ show nk ++ "\n")
                         >>= \b ->
                         toList nr
                         >>= \c ->
                         writer (b ++ c, "go right and join with left part\n")

fromList :: (Show k, Ord k) => [k] -> Writer String (BST k)
fromList [] = writer (Leaf, "list is empty\n")
fromList (x:l) = fromList l
               >>= \b ->
               insert x b
               >>= \c ->
               writer (c, "inserting value " ++ show x ++ "\n")

printTree :: Show k => BST k -> IO()
printTree t = putStr $ TP.verticalPrint (bstToTree t)
