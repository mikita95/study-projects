{-# LANGUAGE RankNTypes #-}
module Task5 where

import           Control.Applicative
import           Data.Functor.Identity

type Lens s t a b = forall f . Functor f => (a -> f b) -> s -> f t
type Lens' s a = Lens s s a a -- (a -> f a) -> s -> f s

set :: Lens' s a -> a -> s -> s
set l a s = runIdentity (l (\_ -> Identity a) s)

view :: Lens' s a -> s -> a
view l s = getConst (l Const s)

over :: Lens' s a -> (a -> a) -> s -> s
over l f s = runIdentity (l (\a -> Identity (f a)) s)

over' :: Lens' s a -> (a -> a) -> s -> s
over' l f s = set l (f $ view l s) s
