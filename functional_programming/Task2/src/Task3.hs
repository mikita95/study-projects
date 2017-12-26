module Task3 where

import           Data.List     (take, unfoldr, zip)
import           System.Random
import qualified TreePrinter   as TP (Tree (Leaf, Node), verticalPrint)

data BST k v = Leaf | Node k v (BST k v) (BST k v)
    deriving (Show, Eq)

isLeaf :: Ord k => (BST k v) -> Bool
isLeaf Leaf = True
isLeaf _    = False

isNode :: Ord k => (BST k v) -> Bool
isNode Leaf = False
isNode _    = True

key :: Ord k => BST k v -> Maybe k
key (Node nk _ _ _) = Just nk
key Leaf            = Nothing

value :: Ord k => (BST k v) -> Maybe v
value (Node _ nv _ _) = Just nv
value Leaf            = Nothing

bstToTree :: (BST k v) -> (TP.Tree k)
bstToTree Leaf               = TP.Leaf
bstToTree (Node nk nv nl nr) = TP.Node nk (bstToTree nl) (bstToTree nr)

find :: Ord k => k -> (BST k v) -> Maybe v
find k Leaf = Nothing
find k (Node nk nv nl nr)
        | k < nk    = find k nl
        | k > nk    = find k nr
        | otherwise = Just nv

insert :: Ord k => k -> v -> (BST k v) -> (BST k v)
insert k v Leaf = Node k v Leaf Leaf
insert k v n@(Node nk nv nl nr)
        | k < nk    = Node nk nv (insert k v nl) nr
        | k > nk    = Node nk nv nl (insert k v nr)
        | otherwise = n

findMin :: Ord k => (BST k v) -> Maybe (BST k v)
findMin Leaf = Nothing
findMin n@(Node nk nv nl nr)
        | isLeaf nl = Just n
        | otherwise  = Just (maybe nl id $ findMin nl)

findMax :: Ord k => (BST k v) -> Maybe (BST k v)
findMax Leaf = Nothing
findMax n@(Node nk nv nl nr)
        | isLeaf nr = Just n
        | otherwise  = Just (maybe nr id $ findMax nr)

delete :: Ord k => k -> (BST k v) -> (BST k v)
delete k Leaf = Leaf
delete k (Node nk nv nl nr)
        | k < nk    = Node nk nv (delete k nl) nr
        | k > nk    = Node nk nv nl (delete k nr)
        | (isNode nl) && (isNode nr) = Node (maybe nk id $ key m) (maybe nv id $ value m) nl (delete (maybe nk id $ key nr) nr)
        | isNode nl = nl
        | otherwise = nr
            where m = maybe Leaf id $ findMin nr

toList :: Ord k => (BST k v) -> [(k,v)]
toList Leaf               = []
toList (Node nk nv nl nr) = (nk,nv) : (toList nl) ++ (toList nr)

fromList :: Ord k => [(k,v)] -> (BST k v)
fromList []    = Leaf
fromList (x:r) =  insert (fst x) (snd x) $ fromList r

tree1 :: BST Int String
tree1 = Node 5 "five" Leaf Leaf

tree2 :: BST Int String
tree2 = insert 10 "ten" $ insert 6 "six" tree1

tree3 :: BST Int String
tree3 = insert 3 "three" $ insert 11 "eleven" $ insert 7 "seven" $ insert 0 "zero" $ insert 2 "two" tree2

randomList :: Int -> StdGen -> [Int]
randomList n = take n . unfoldr (Just . random)

printTree :: Show k => BST k v -> IO()
printTree t = putStr $ TP.verticalPrint (bstToTree t)
