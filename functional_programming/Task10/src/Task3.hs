{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE QuasiQuotes       #-}
{-# LANGUAGE TemplateHaskell   #-}
module Task3 where

import           Data.List                     (intercalate)
import           Language.Haskell.TH
import           Language.Haskell.TH.Syntax

emptyShow :: Name -> Q [Dec]
emptyShow name = [d|instance Show $(conT name) where show _ = ""|]

listFields :: Name -> Q [Dec]
listFields name = do
    TyConI (DataD _ _ _ _ (RecC ctr fields :xs) _) <- reify name
    let names = map (\(n, _ ,_) -> n) fields
        ctrName = showName ctr
        showField name = let s = nameBase name in
                         [|\x -> "    " ++ s ++ " = " ++ show ($(varE name) x)|]
        showFields = listE $ map showField names
    [d|instance Show $(conT name) where
        show x = ctrName ++ " {\n" ++ intercalate ",\n" (map ($ x) $showFields) ++ "\n}"|]
