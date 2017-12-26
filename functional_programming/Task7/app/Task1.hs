{-# LANGUAGE OverloadedStrings #-}

module Task1 where

import           Data.Array.IO
import           Data.IORef
import           Data.String        (fromString)
import qualified Data.Text          as T
import           System.Environment
import           System.IO

findProperties :: T.Text -> IO [(T.Text, T.Text)]
findProperties filename = do allLines <- lines <$> readFile (T.unpack filename)
                             return $ map (splitOnPair "=" . fromString) allLines

splitOnPair :: T.Text -> T.Text -> (T.Text, T.Text)
splitOnPair p s = let splitted = T.splitOn p s in
                  (head splitted, T.unwords $ tail splitted)

listToIOArray :: [a] -> IO (IOArray Int a)
listToIOArray l = newListArray (0, length l - 1) l

properter :: IOArray Int (T.Text, T.Text) -> IO ()
properter vals = do
    putStr "> "
    hFlush stdout
    input <- getLine
    case splitOnPair " " (T.pack input) of
        ("INS", property) -> do oldVals <- getElems vals
                                pair <- newIORef $ splitOnPair " " property
                                a1 <- readIORef pair
                                if elem (fst a1) $ map fst oldVals then
                                    do putStrLn $ "property " ++ show (fst a1) ++
                                                  " already exists"
                                       properter vals
                                else do newVals <- listToIOArray $ oldVals ++ [a1]
                                        putStrLn $ "new property " ++ show (fst a1) ++
                                                   " with value " ++ show (snd a1) ++
                                                   " has been inserted"
                                        properter newVals
        ("MOD", property) -> do oldVals <- getElems vals
                                pair <- newIORef $ splitOnPair " " property
                                a1 <- readIORef pair
                                newVals <- listToIOArray $ map (\p -> if fst p == fst a1 then
                                                                      (fst p, snd a1)
                                                                      else p) oldVals
                                putStrLn $ "property " ++ show (fst a1) ++
                                           " has beem modified with value " ++ show (snd a1)
                                properter newVals
        ("W", wrFile)     -> do oldVals <- getElems vals
                                written <- newIORef $ map (\p -> T.unpack (fst p) ++ "=" ++ T.unpack (snd p)) oldVals
                                w0 <- readIORef written
                                withFile (T.unpack wrFile) WriteMode $ \f -> mapM_ (hPutStrLn f) w0
                                putStrLn $ "File `" ++ T.unpack wrFile ++
                                           "` was written, all changes saved"
                                properter vals
        ("Q", "")         -> return ()
        p                 -> do putStrLn $ "Unknown command " ++ show p
                                properter vals

main' :: IO()
main' = do args  <- getArgs
           case args of
               [filename] -> do properties <- findProperties (fromString filename)
                                vals <- listToIOArray properties
                                putStr "\nInteractive options:\n\
                                        \\t*INS <property> <value>\t : insert new value\n\
                                        \\t*MOD <property> <value>\t : modify previous value\n\
                                        \\t*W <file>\t\t : write to file\n\
                                        \\t*Q\t\t\t : quit\n"
                                properter vals
               _          -> putStr $ "wrong args: " ++ show args
