{-# LANGUAGE FlexibleContexts #-}
module Task1 where

import           Control.Monad    (unless)
import           Control.Monad.ST (ST, runST)
import           Data.Foldable    (forM_)
import           Data.STRef

data ArrayList s a = Empty
                   | Array (STRef s a) (STRef s (ArrayList s a))

get :: STRef s a -> ST s a
get = readSTRef

set :: STRef s a -> a -> ST s ()
set = writeSTRef

var :: a -> ST s (STRef s a)
var = newSTRef

fromList :: [a] -> ST s (ArrayList s a)
fromList [] = return Empty
fromList (x:xs) = do
    y <- var x
    ys <- fromList xs >>= var
    return (Array y ys)

toList :: ArrayList s a -> ST s [a]
toList Empty = return []
toList (Array x xs) = do
    y <- get x
    ys <- get xs >>= toList
    return (y:ys)

append :: ArrayList s a -> ArrayList s a -> ST s (ArrayList s a)
append x y = case x of
    Empty -> return y
    Array v xs -> do
        xs' <- get xs
        ys' <- append xs' y >>= var
        return (Array v ys')

pushBack :: ArrayList s a -> a -> ST s ()
pushBack Empty _ = error "List is empty"
pushBack (Array _ xs) e = do
    xs' <- get xs
    case xs' of
        Empty -> do e' <- var e
                    t <- var Empty
                    set xs (Array e' t)
        _     -> pushBack xs' e


popBack :: ArrayList s a -> ST s a
popBack Empty = error "List is empty"
popBack (Array _ xs) = do
    xs' <- get xs
    case xs' of
        Empty        -> error "Array from one element"
        (Array y ys) -> do ys' <- get ys
                           case ys' of
                               Empty -> do y' <- get y
                                           set xs Empty
                                           return y'
                               _     -> popBack xs'

getByIndex :: ArrayList s a -> Int -> ST s a
getByIndex Empty _ = error "List is empty"
getByIndex (Array x xs) i = case i of
    0 -> get x
    _ -> do xs' <- get xs
            getByIndex xs' (i - 1)

setByIndex :: ArrayList s a -> Int -> a -> ST s ()
setByIndex Empty _ _ = error "List is empty"
setByIndex (Array x xs) i e = case i of
    0 -> set x e
    _ -> do xs' <- get xs
            setByIndex xs' (i - 1) e

concat :: [Int] -> [Int] -> [Int]
concat a b = runST $ do
    a' <- fromList a
    b' <- fromList b
    z <- append a' b'
    toList z

sort :: [Int] -> [Int]
sort list = runST $ do
    let listSize = length list
    arr <- fromList list
    forM_ [1..listSize - 1] $ \i ->
        forM_ [i-1, i-2..0] $ \j -> do
            cur <- getByIndex arr j
            next <- getByIndex arr (j + 1)
            unless (cur <= next) $ do setByIndex arr j next
                                      setByIndex arr (j + 1) cur
    toList arr


test :: [Int] -> [Int]
test l = runST $ do
    x <- fromList l
    toList x

