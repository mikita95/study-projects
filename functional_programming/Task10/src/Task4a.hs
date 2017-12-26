{-# LANGUAGE TemplateHaskell #-}

module Task4a where

import           Task4

r1 :: Integer
r1 = $(fibQ 10)
