{-# LANGUAGE OverloadedStrings #-}

module Task3 where

import           Control.Monad              (forever, void)
import           Control.Monad.IO.Class     (liftIO)
import           Control.Monad.Trans.Class  (lift)
import           Control.Monad.Trans.Either (EitherT (..), left)
import           Control.Monad.Trans.Reader (ReaderT (..), ask)
import           Control.Monad.Trans.State  (StateT (..), get, put)
import           Data.Array.IO              (IOArray (..), getElems, newListArray)
import           Data.IORef                 (newIORef, readIORef)
import           Data.String                (fromString)
import qualified Data.Text                  as T
import           System.Environment         (getArgs)
import           System.IO                  (IO (..), IOMode (..), hFlush, hPutStrLn,
                                             stdout, withFile)

type Properties = [(T.Text, T.Text)]
type FileText = [T.Text]
type PropertiesReader = ReaderT FileText IO Properties

findProperties :: PropertiesReader
findProperties = do
    filetext <- ask
    return $ map (splitOnPair "=") filetext

splitOnPair :: T.Text -> T.Text -> (T.Text, T.Text)
splitOnPair p s = let splitted = T.splitOn p s in
                  (head splitted, T.unwords $ tail splitted)

listToIOArray :: [a] -> IO (IOArray Int a)
listToIOArray l = newListArray (0, length l - 1) l

loop :: (Monad m) => EitherT e m a -> m e
loop = fmap (either id id) . runEitherT . forever

quit :: (Monad m) => e -> EitherT e m r
quit = left

type IOProperties = IOArray Int (T.Text, T.Text)
type Properter = StateT IOProperties IO ()

properter :: Properter
properter = loop $ do
    vals <- lift get
    liftIO $ putStr "> "
    liftIO $ hFlush stdout
    input <- liftIO getLine
    oldVals <- liftIO $ getElems vals
    case splitOnPair " " (T.pack input) of
        ("INS", property) -> do pair <- liftIO $ newIORef $ splitOnPair " " property
                                a1 <- liftIO $ readIORef pair
                                if elem (fst a1) $ map fst oldVals then
                                    do liftIO $ putStrLn $ "property " ++ show (fst a1) ++
                                                  " already exists"
                                       lift $ put vals
                                else do newVals <- liftIO $ listToIOArray $ oldVals ++ [a1]
                                        liftIO $ putStrLn $ "new property " ++ show (fst a1) ++
                                                   " with value " ++ show (snd a1) ++
                                                   " has been inserted"
                                        lift $ put newVals
        ("MOD", property) -> do pair <- liftIO $ newIORef $ splitOnPair " " property
                                a1 <- liftIO $ readIORef pair
                                newVals <- liftIO $ listToIOArray $ map (\p -> if fst p == fst a1 then
                                                                      (fst p, snd a1)
                                                                      else p) oldVals
                                liftIO $ putStrLn $ "property " ++ show (fst a1) ++
                                           " has beem modified with value " ++ show (snd a1)
                                lift $ put newVals
        ("W", wrFile)     -> do oldVals <- liftIO $ getElems vals
                                written <- liftIO $ newIORef $ map (\p -> T.unpack (fst p) ++ "=" ++ T.unpack (snd p)) oldVals
                                w0 <- liftIO $ readIORef written
                                liftIO $ withFile (T.unpack wrFile) WriteMode $ \f -> mapM_ (hPutStrLn f) w0
                                liftIO $ putStrLn $ "File `" ++ T.unpack wrFile ++
                                           "` was written, all changes saved"
                                lift $ put vals
        ("Q", "")         -> quit ()
        p                 -> do liftIO $ putStrLn $ "Unknown command " ++ show p
                                lift $ put vals

main' :: IO()
main' = do args  <- getArgs
           case args of
               [filename] -> do
                                filetext <- lines <$> readFile filename
                                properties <- runReaderT findProperties (T.pack <$> filetext)
                                vals <- listToIOArray properties
                                putStr "\nInteractive options:\n\
                                        \\t*INS <property> <value>\t : insert new value\n\
                                        \\t*MOD <property> <value>\t : modify previous value\n\
                                        \\t*W <file>\t\t : write to file\n\
                                        \\t*Q\t\t\t : quit\n"
                                void $ runStateT properter vals
               _          -> putStr $ "wrong args: " ++ show args
