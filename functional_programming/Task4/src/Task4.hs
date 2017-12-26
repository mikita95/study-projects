{-# LANGUAGE NoImplicitPrelude #-}

module Task4 where

import           Task3

data State s v = State { newState :: s -> (s, v) }

instance Monad (State s) where
    return v = State (\s -> (s, v))
    (>>=) x f = State newFun
      where
        newFun s = let (oldS, oldV) = newState x s
                   in newState (f oldV) oldS

