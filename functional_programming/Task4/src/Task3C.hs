{-# LANGUAGE FlexibleInstances    #-}
{-# LANGUAGE NoImplicitPrelude    #-}
{-# LANGUAGE UndecidableInstances #-}

module Task3C where

import           Prelude (id)
import           Task3

instance MonadFish m => Monad m where
    return = returnFish
    (>>=) x f = (>=>) id f x

