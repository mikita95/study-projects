module Task4
    ( SplayTree(..)
    , head
    , tail
    , singleton
    , empty
    , isEmpty
    , fromList
    , fromSortedList
    , toList
    , toSortedList
    , insert
    , lookup
    , (!!)
    , splay
    , subtreeSize
    , delete
    ) where

import           Prelude     hiding (head, lookup, tail, (!!))
import qualified TreePrinter as TP (Tree (Leaf, Node), verticalPrint)

data SplayTree k v = Leaf
                   | Node k v Int (SplayTree k v) (SplayTree k v)
                   deriving (Ord, Eq)

instance (Ord k) => Functor (SplayTree k) where
    fmap _ Leaf             = Leaf
    fmap f (Node k v h l r) = Node k (f v) h (fmap f l) (fmap f r)

singleton :: (Ord k) => (k,v) -> SplayTree k v
singleton (k,v) = Node k v 0 Leaf Leaf

empty :: (Ord k) => SplayTree k v
empty = Leaf

isEmpty :: (Ord k) => SplayTree k v -> Bool
isEmpty Leaf = True
isEmpty _    = False

subtreeSize :: (Ord k) => SplayTree k v -> Int
subtreeSize Leaf             = 0
subtreeSize (Node _ _ d _ _) = d

lookup :: (Ord k) => k -> SplayTree k v -> SplayTree k v
lookup _ Leaf = Leaf
lookup k' t@(Node k _ _ l r)
    | k' == k   = t
    | k > k'    = case lookup k' l of
                      Leaf -> t
                      lt   -> zig lt t
    | otherwise = case lookup k' r of
                      Leaf -> t
                      rt   -> zag t rt

(!!) :: (Ord k) => SplayTree k v -> Int -> (k,v)
(!!) Leaf _ = error "Index is out if bounds"
(!!) (Node k v d l r) n =
    if n > d then error "Index is out if bounds"
    else
        let l' = subtreeSize l in
        if n == l'
        then (k,v)
        else if n <= l'
             then l !! n
             else r !! (n - l')

splay :: (Ord k) => SplayTree k v -> Int -> SplayTree k v
splay Leaf _ = error "Splay error"
splay t@(Node _ _ d l r) n =
    if n > d
    then error "Index is out if bounds"
    else
        let l' = subtreeSize l in
        if n == l' then t
        else if n <= l'
            then case splay l n of
                     Leaf -> error "Splay error"
                     lt   -> zig lt t
            else case splay r (n - l') of
                     Leaf -> error "Splay error"
                     rt   -> zag t rt

zig :: (Ord k) => SplayTree k v -> SplayTree k v -> SplayTree k v
zig Leaf _ = error "Splay error: zig is impossible"
zig _ Leaf = error "Splay error: zig is impossible"
zig (Node k1 v1 _ l1 r1) (Node k v d _ r) =
    Node k1 v1 d l1 (Node k v (d - subtreeSize l1 - 1) r1 r)

zag :: (Ord k) => SplayTree k v -> SplayTree k v -> SplayTree k v
zag Leaf _ = error "Splay error: zag is impossible"
zag _ Leaf = error "Splay error: zag is impossible"
zag (Node k v d l _) (Node k1 v1 _ l1 r1) =
    Node k1 v1 d (Node k v (d - subtreeSize r1 - 1) l l1) r1

insert :: (Ord k) => k -> v -> SplayTree k v -> SplayTree k v
insert k v t =
    case lookup k t of
        Leaf               -> Node k v 0 Leaf Leaf
        (Node k1 v1 d l r) -> if k1 < k
                              then Node k v (d + 1) (Node k1 v1 (d - subtreeSize r + 1) l Leaf) r
                              else Node k v (d + 1) l (Node k1 v1 (d - subtreeSize l + 1) Leaf r)

head :: (Ord k) => SplayTree k v -> (k,v)
head Leaf             = error "Tree is empty"
head (Node k v _ _ _) = (k,v)

tail :: (Ord k) => SplayTree k v -> SplayTree k v
tail Leaf = error "Tree is empty"
tail (Node _ _ _ Leaf r) = r
tail (Node _ _ _ l Leaf) = l
tail (Node _ _ _ l r)    = case splayMax l of
                               (Node k v d l1 Leaf) -> Node k v (d + subtreeSize r) l1 r
                               _                    -> error "Splay error"

delete :: (Ord k) => k -> SplayTree k v -> SplayTree k v
delete _ Leaf = Leaf
delete k t = case lookup k t of
                 t'@(Node k1 _ _ _ _) -> if k == k1
                                         then tail t'
                                         else t'
                 Leaf                 -> error "Splay error"

merge :: (Ord k) => SplayTree k v -> SplayTree k v -> SplayTree k v
merge Leaf b = b
merge a Leaf = a
merge a b = let (Node k v d l _) = splayMax a in
            Node k v (d + 1 + subtreeSize b) l b

splayMax :: (Ord k) => SplayTree k v -> SplayTree k v
splayMax Leaf = Leaf
splayMax h@(Node _ _ _ _ Leaf) = h
splayMax (Node k1 v1 d1 l1 (Node k2 v2 _ l2 r2)) =
    splayMax (Node k2 v2 d1 (Node k1 v1 (d1 - subtreeSize r2) l1 l2) r2)

splayMin :: (Ord k) => SplayTree k v -> SplayTree k v
splayMin Leaf = Leaf
splayMin h@(Node _ _ _ Leaf _) = h
splayMin (Node k1 v1 d1 (Node k2 v2 _ l2 r2) r1) =
    splayMin (Node k2 v2 d1 l2 (Node k1 v1 (d1 - subtreeSize l2) r2 r1))

fromList :: (Ord k) => [(k,v)] -> SplayTree k v
fromList [] = Leaf
fromList l  = foldl (\t (k,v) -> insert k v t) Leaf l

fromSortedList :: (Ord k) => [(k,v)] -> SplayTree k v
fromSortedList = fromList

toList :: (Ord k) => SplayTree k v -> [(k,v)]
toList = toSortedList

toSortedList :: (Ord k) => SplayTree k v -> [(k,v)]
toSortedList h@(Node _ _ _ Leaf _) = head h : toSortedList (tail h)
toSortedList Leaf                  = []
toSortedList h                     = toSortedList $ splayMin h

splayTreeToTree :: SplayTree k v -> TP.Tree k
splayTreeToTree Leaf = TP.Leaf
splayTreeToTree (Node nk _ _ nl nr) = TP.Node nk (splayTreeToTree nl) (splayTreeToTree nr)

printTree :: Show k => SplayTree k v -> IO()
printTree t = putStr $ TP.verticalPrint (splayTreeToTree t)
