{-# LANGUAGE TemplateHaskell #-}

module Task3a where

import           Task3

data MyData = MyData
    { foo :: String
    , bar :: Int
    }

listFields ''MyData
