module Task1 where

import Prelude (Foldable, Monoid, mempty, mappend, undefined, id, (.))

foldMap :: (Monoid m, Foldable t) => (a -> m) -> t a -> m
foldMap = undefined

newtype Endo a = Endo { appEndo :: a -> a }

instance Monoid (Endo a) where
    mempty = Endo id
    mappend (Endo f) (Endo g) = Endo (f . g)

foldr :: Foldable t => (a -> b -> b) -> b -> t a -> b
foldr f z t = appEndo (foldMap (Endo . f) t) z
