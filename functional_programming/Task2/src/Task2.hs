module Task2 where

data Equipment = Equipment
    { title           :: String
    , changeAttackOn  :: Int
    , changeDefenseOn :: Int
    } deriving Show

instance Eq Equipment where
    (Equipment _ a1 b1) == (Equipment _ a2 b2) = [a1,b1] == [a2,b2]

instance Ord Equipment where
    (Equipment _ a1 b1) `compare` (Equipment _ a2 b2) = [a1,b1] `compare` [a2,b2]

class Creature a where
    name :: a -> String
    health :: a -> Int
    attack :: a -> Int
    defense :: a -> Int
    equipment :: a -> Maybe Equipment
    setHealth :: a -> Int -> a
    changeEquipmentOnMax :: a -> (Maybe Equipment) -> a

data Player = Player String Int Int Int (Maybe Equipment) deriving Show
data Monster = Monster String Int Int Int (Maybe Equipment) deriving Show

instance Creature Player where
    name (Player n h a d e)   = n
    health (Player n h a d e) = h
    attack (Player n h a d e) = a
    defense (Player n h a d e) = d
    equipment (Player n h a d e) = e
    setHealth (Player n h a d e) newH = Player n newH a d e
    changeEquipmentOnMax (Player n h a d e) newE
                                    | newE == Nothing = Player n h a d e
                                    | otherwise = Player n h a d (max e newE)


instance Creature Monster where
    name (Monster n h a d e)   = n
    health (Monster n h a d e) = h
    attack (Monster n h a d e) = a
    defense (Monster n h a d e) = d
    equipment (Monster n h a d e) = e
    setHealth (Monster n h a d e) newH = Monster n newH a d e
    changeEquipmentOnMax (Monster n h a d e) newE
                                    | newE == Nothing  = Monster n h a d e
                                    | otherwise = Monster n h a d (max e newE)


data Result a = Lose | Win a
    deriving Show

result :: b -> (a -> b) -> Result a -> b
result n _ Lose    = n
result _ f (Win x) = f x

changeHealth :: Creature a => a -> Int -> a
changeHealth c h = setHealth c (health c - h)

getEnsureByMonster :: Player -> Monster -> Int
getEnsureByMonster a b = (attack a) + (fromEquipment a changeAttackOn) - (defense b) - (fromEquipment b changeDefenseOn)

getEnsureByPlayer :: Monster -> Player -> Int
getEnsureByPlayer a b = (attack a) - (defense b) - (fromEquipment b changeDefenseOn)

fromEquipment :: Creature a => a -> (Equipment -> Int) -> Int
fromEquipment c f = maybe 0 f (equipment c)

ensureMonster :: Player -> Monster -> Monster
ensureMonster p m = changeHealth m (getEnsureByMonster p m)

ensurePlayer :: Monster -> Player -> Player
ensurePlayer m p = changeHealth p (getEnsureByPlayer m p)

battleRound :: Player -> Monster -> Bool -> Result Player
battleRound p m s
    | health p <= 0 = Lose
    | health m <= 0 = win p m
    | otherwise = if (s) then battleRound p (ensureMonster p m) (not s)
                  else battleRound (ensurePlayer m p) m (not s)

win :: Player -> Monster -> Result Player
win p m  = Win (changeEquipmentOnMax p (equipment m))

gloriousBattle :: Player -> [Monster] -> Result Player
gloriousBattle p ms
                | health p <= 0 = Lose
                | null ms = Win p
                | otherwise = gloriousBattle (result (setHealth p 0) id r) (tail ms)
                    where r = battleRound p m True
                          m = head ms

ace :: Equipment
ace = Equipment { title = "ace"
                , changeAttackOn = 5
                , changeDefenseOn = -1
                }

hauberk :: Equipment
hauberk = Equipment { title = "hauberk"
                    , changeAttackOn = -2
                    , changeDefenseOn = 3
                    }

ball :: Equipment
ball = Equipment { title = "ball"
                 , changeAttackOn = 10
                 , changeDefenseOn = 5
                 }

ivan :: Player
ivan = Player "Ivan" 100 30 20 (Nothing)

frog :: Monster
frog = Monster "Frog" 20 5 3 (Just ace)

snake :: Monster
snake = Monster "Snake" 90 20 10 (Just ball)





