{-# LANGUAGE FlexibleInstances     #-}
{-# LANGUAGE MultiParamTypeClasses #-}

module Task4A where

import           Control.Applicative ((<|>))
import           Data.List           (lookup, (++))
import           Prelude             hiding (fst, snd)
import           Data.Maybe          (isNothing)
import qualified Task2               as T (BST (..), next, delete, singleton)
import qualified Task3               as S

class Map t k v where
    emptyMap :: t (Pair k v)
    toList   :: t (Pair k v) -> [(k,v)]
    find     :: k -> t (Pair k v) -> Maybe v
    insert   :: (k, v) -> t (Pair k v) -> t (Pair k v)
    delete   :: (k, v) -> t (Pair k v) -> t (Pair k v)
    next     :: (k, v) -> t (Pair k v) -> Maybe (k, v)
    fromList :: [(k,v)] -> t (Pair k v)

data Pair k v = Pair
    { fst :: k
    , snd :: v
    } deriving (Show)

instance (Eq k) => Eq (Pair k v) where
    (==) a b = fst a == fst b

instance (Ord k) => Ord (Pair k v) where
    compare a b = compare (fst a) (fst b)

toStdPair :: Pair k v -> (k, v)
toStdPair (Pair k v) = (k, v)

fromStdPair :: (k, v) -> Pair k v
fromStdPair (k, v) = Pair k v

toStdPairList :: [Pair k v] -> [(k, v)]
toStdPairList = map toStdPair

fromStdPairList :: [(k, v)] -> [Pair k v]
fromStdPairList = map fromStdPair

instance (Ord k) => Map T.BST k v where
    emptyMap        = mempty
    toList          = foldr (\x -> (++) [toStdPair x]) []
    find k t        = lookup k (toList t) <|> Nothing
    insert (k, v) t = mappend t (T.singleton (Pair k v))
    delete k t      = T.delete (fromStdPair k) t
    next k t        = fmap  toStdPair (T.next (fromStdPair k) t)
    fromList        = foldr insert emptyMap
    
mergeMaybes :: Maybe a -> Maybe a -> Maybe a
mergeMaybes a b
    | isNothing a = b
    | isNothing b = a

