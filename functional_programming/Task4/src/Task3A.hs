{-# LANGUAGE FlexibleInstances    #-}
{-# LANGUAGE NoImplicitPrelude    #-}
{-# LANGUAGE UndecidableInstances #-}

module Task3A where

import           Task3

instance Monad m => MonadFish m where
    returnFish = return
    (>=>) f g = (\x -> f x >>= g)
