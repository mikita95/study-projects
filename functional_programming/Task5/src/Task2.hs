module Task2 where

import           Control.Applicative (Alternative (..))
import           Control.Monad       (void, (>=>))
import           Data.Bifunctor      (first)
import           Data.Char           (isDigit, isUpper)

newtype Parser a = Parser { runParser :: String -> Maybe (a, String) }

instance Functor Parser where
    fmap f (Parser g) = Parser (fmap (first f) . g)

instance Applicative Parser where
    pure a = Parser (\s -> Just (a,s))
    (<*>) (Parser f) p = Parser (f >=>
                                (\(h,s') -> runParser (fmap h p) s'))

satisfy :: (Char -> Bool) -> Parser Char
satisfy p = Parser f
  where
    f [] = Nothing
    f (x:xs)
        | p x = Just (x, xs)
        | otherwise = Nothing

char :: Char -> Parser Char
char c = satisfy (== c)

abParser :: Parser (Char, Char)
abParser = (,) <$> char 'a' <*> char 'b'

abParser_ :: Parser ()
abParser_ = void abParser

posInt :: Parser Integer
posInt = Parser f
  where
    f xs
        | null ns = Nothing
        | otherwise = Just (read ns, rest)
      where (ns, rest) = span isDigit xs

intPair :: Parser [Integer]
intPair = (\a _ b -> [a,b]) <$> posInt <*> char ' ' <*> posInt

instance Alternative Parser where
    empty = Parser $ const Nothing
    (<|>) x y = Parser (\s -> runParser x s <|> runParser y s)

intOrUppercase :: Parser ()
intOrUppercase =  void posInt <|> void (satisfy isUpper)
