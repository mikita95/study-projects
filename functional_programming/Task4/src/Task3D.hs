{-# LANGUAGE FlexibleInstances    #-}
{-# LANGUAGE NoImplicitPrelude    #-}
{-# LANGUAGE UndecidableInstances #-}

module Task3D where

import           Prelude (id)
import           Task3

instance MonadFish m => MonadJoin m where
    returnJoin = returnFish
    join       = (>=>) id id

