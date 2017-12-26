{-# LANGUAGE FlexibleInstances     #-}
{-# LANGUAGE MultiParamTypeClasses #-}

module Task3 where

import           Data.Maybe  (isNothing)
import           Data.Monoid (mappend, mempty)
import qualified Task2       as T (BST (..), delete, find, insert, next, singleton)

class Set t k where
    emptySet :: t k
    find :: k -> t k -> Maybe k
    insert :: k -> t k -> t k
    delete :: k -> t k -> t k
    next :: k -> t k -> Maybe k
    toList :: t k -> [k]
    fromList :: [k] -> t k

instance (Ord k) => Set T.BST k where
    emptySet    = mempty
    find k      = foldr (\v -> if k == v
                               then mergeMaybes (Just v)
                               else mergeMaybes Nothing) Nothing
    insert k t  = mappend t (T.singleton k)
    delete      = T.delete
    next        = T.next
    toList      = foldr (:) []
    fromList    = foldr insert emptySet

mergeMaybes :: Maybe a -> Maybe a -> Maybe a
mergeMaybes a b
    | isNothing a = b
    | isNothing b = a

singleSet :: Ord k => k -> T.BST k
singleSet n = insert n emptySet
