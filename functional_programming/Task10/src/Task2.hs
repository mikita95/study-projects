{-# LANGUAGE TemplateHaskell #-}
module Task2 where

import           Control.Monad
import           Language.Haskell.TH
import           Language.Haskell.TH.Syntax (Lift (..))
import           System.Environment

env :: String -> Q String
env e = runIO (getEnv e)

envExp :: String -> Q Exp
envExp e = sigE (lift =<< env e) [t| String |]
