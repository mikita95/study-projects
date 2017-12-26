{-# LANGUAGE FlexibleInstances    #-}
{-# LANGUAGE NoImplicitPrelude    #-}
{-# LANGUAGE UndecidableInstances #-}

module Task3B where

import           Prelude (id)
import           Task3

instance Monad m => MonadJoin m where
    returnJoin = return
    join x = x >>= id

