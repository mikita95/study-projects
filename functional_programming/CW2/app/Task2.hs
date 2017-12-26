module Task2 where

import           Data.IORef
import           System.Environment
import           System.IO
import           Control.Exception



checkNumber :: String -> IO (Either SomeException Int)
checkNumber s = try (evaluate (read s))

numbersCheck :: Int -> IO (String)
numbersCheck number = do if (number <= 0) then
                             do return "Correct"
                         else do input <- getLine
                                 result <- checkNumber input
                                 case result of
                                     Left ex   -> return "Bad"
                                     Right val -> numbersCheck (number - 1)

main' :: IO()
main' = do args  <- getArgs
           case args of
               [number]   -> do result <- numbersCheck (read number)
                                putStr $ result
               _          -> putStr $ "wrong args: " ++ show args
