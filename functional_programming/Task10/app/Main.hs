{-# LANGUAGE TemplateHaskell #-}
module Main where

import Lib
import Task2

main :: IO ()
main = do putStr "TH_ENV="
          putStr $(envExp "TH_ENV")
          
