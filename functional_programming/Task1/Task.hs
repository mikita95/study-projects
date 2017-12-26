module Task where
import           Control.Applicative
import           Data.Char
import           Data.List
import           System.Random       (newStdGen, randomRs)
import           Text.Read

strToIntList :: String -> [Int]
strToIntList =  map myRead . words

stringSum :: String -> Int
stringSum = sum . strToIntList

myRead :: String -> Int
myRead s
    | length s > 1 && isPrefixOf "+" s && isDigit (head $ tail s) = read (tail s)
    | otherwise = read s

tests = [ "1", "1 2 3", " 1", "1 ", "\t1\t", "\t12345\t", "010 020 030"
        , " 123 456 789 ", "-1", "-1 -2 -3", "\t-12345\t", " -123 -456 -789 "
        , "\n1\t\n3   555  -1\n\n\n-5", "123\t\n\t\n\t\n321 -4 -40"
        ]

mustFail = ["asd", "1-1", "1.2", "--2", "+1", "1+"]

advancedTests    = [ "+1", "1 +1", "-1 +1", "+1 -1"]

advancedMustFail = ["1+1", "++1", "-+1", "+-1", "1 + 1"]

zipN :: ([a] -> b) -> [[a]] -> [b]
zipN _ [] = []
zipN f x  = map f (transpose x)
--zipN f x = if any null x then []
--         else (f (map head l)) : zipN f (map tail l)

randomIntList :: Int -> Int -> Int -> IO [Int]
randomIntList n from to = take n . randomRs (from, to) <$> newStdGen

mergeLists :: [Int] -> [Int] -> [Int]
mergeLists a [] = a
mergeLists [] b = b
mergeLists a b = if (ah) < (bh) then (ah) : (mergeLists (tail a) b)
                 else (bh) : (mergeLists a (tail b))
                 where ah = head a
                       bh = head b

mergeSort :: [Int] -> [Int]
mergeSort [] = []
mergeSort (x:[]) = x:[]
mergeSort x = mergeLists (mergeSort left) (mergeSort right)
              where mid = length x `div` 2
                    left = take mid x
                    right = drop mid x

