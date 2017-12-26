module Task1 where

import           Control.Applicative (Applicative, pure, (<*>))
import           Data.Foldable
import           Data.Functor
import           Data.Monoid
import           Data.Traversable
import           Prelude             (Show, ($))

-- Identity a

newtype Identity a = Identity a
    deriving (Show)

instance Functor Identity where
    fmap f (Identity x) = Identity (f x)

instance Applicative Identity where
    pure = Identity
    (<*>) (Identity f) (Identity x) = Identity $ f x

instance Foldable Identity where
    foldMap f (Identity x) = f x

instance Traversable Identity where
    traverse f (Identity x) = fmap Identity (f x)

-- Either a b

data Either a b = Left a 
                  | Right b
                  deriving (Show)

instance Functor (Either a) where
    fmap _ (Left a)  = Left a
    fmap f (Right b) = Right (f b)

instance Applicative (Either a) where
    pure = Right
    (<*>) (Left a) _  = Left a
    (<*>) (Right f) x = fmap f x

instance Foldable (Either a) where
    foldMap _ (Left _)  = mempty
    foldMap f (Right b) = f b

instance Traversable (Either a) where
    traverse _ (Left a)  = pure (Left a)
    traverse f (Right b) = fmap Right (f b)

-- Tree a

data Tree a = Leaf 
              | Node a (Tree a) (Tree a)
              deriving (Show)

instance Functor Tree where
    fmap _ Leaf           = Leaf
    fmap f (Node a nl nr) = Node (f a) (fmap f nl) (fmap f nr)

instance Applicative Tree where
    pure a = Node a (pure a) (pure a)
    (<*>) Leaf _                        = Leaf
    (<*>) _ Leaf                        = Leaf
    (<*>) (Node f fl fr) (Node a nl nr) = Node (f a) (fl <*> nl) (fr <*> nr)

instance Foldable Tree where
    foldMap _ Leaf         = mempty
    foldMap f (Node k l r) = foldMap f l <> f k <> foldMap f r

instance Traversable Tree where
    traverse _ Leaf           = pure Leaf
    traverse f (Node a nl nr) = fmap Node (f a) <*> traverse f nl <*> traverse f nr

-- Const a b

newtype Const a b = Const a
    deriving (Show)

instance Functor (Const a) where
    fmap _ (Const a) = Const a

instance Monoid a => Applicative (Const a) where
    pure _ = Const mempty
    (<*>) (Const x) (Const a) = Const (x <> a)

instance Foldable (Const a) where
    foldMap _ (Const _) = mempty

instance Traversable (Const a) where
    traverse _ (Const a) = pure (Const a)

-- Pair a b

data Pair a b = Pair a b
    deriving (Show)

instance Functor (Pair a) where
    fmap f (Pair a b) = Pair a (f b)

instance Monoid a => Applicative (Pair a) where
    pure = Pair mempty
    (<*>) (Pair x f) (Pair y b) = Pair (x <> y) (f b)

instance Foldable (Pair a) where
    foldMap f (Pair _ b) = f b

instance Traversable (Pair a) where
    traverse f (Pair a b) = fmap (Pair a) (f b)
