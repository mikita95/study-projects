module Task1 where

safeTail :: [a] -> Maybe [a]
safeTail [] = Nothing
safeTail x  = Just (tail x)

safeInit :: [a] -> Maybe [a]
safeInit [] = Nothing
safeInit x  = Just (init x)

strip :: [a] -> [a]
strip x = maybe [] id $ safeTail $ maybe [] id $ safeInit x

safeTail2 :: [a] -> Either String [a]
safeTail2 [] = Left "List is empty."
safeTail2 x  = Right (tail x)

safeInit2 :: [a] -> Either String [a]
safeInit2 [] = Left "List is empty"
safeInit2 x  = Right (init x)

strip2 :: [a] -> [a]
strip2 x = either (\a -> []) id $ safeTail2 $ either (\a -> []) id $ safeInit2 x


