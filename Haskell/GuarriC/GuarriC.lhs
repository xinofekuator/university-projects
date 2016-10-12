> module GuarriC where
> import Data.Maybe
> import Control.Monad

Sintaxis abstracta de NanoC:

> data Programa  = Prog Bloque
> type Bloque    = [Comando]
> data Comando   = Asig String Exp
>                | If Exp Bloque Bloque
>                | While Exp Bloque
>                | Print Exp
> data BTree a b = Leaf a | Node (BTree a b) b (BTree a b)
> type Exp       = BTree LitVar Op
> data LitVar    = Lint Integer | Var String
> data Op        = Add | Sub | Mul | And | Or | Eq | Gt

Ejemplos de expresiones

> exps :: [Exp]
> exps =
>  [ Leaf (Lint 1)
>  , Leaf (Lint 0)
>  , Node (Leaf (Lint 0)) And (Leaf (Lint 1))
>  , Node (Leaf (Lint 1)) And (Leaf (Lint 1))
>  , Node (Leaf (Lint 0)) Or (Leaf (Lint 1))
>  , Node (Leaf (Lint 0)) Or (Leaf (Lint 0))
>  , Node (Leaf (Lint 8)) Add (Leaf (Lint 10))
>  , Node (Node (Leaf (Lint 1)) Mul (Leaf (Lint 3)))
>         Add
>         (Node (Leaf (Lint 4)) Mul (Leaf (Lint 9)))
>  , Node (Node (Leaf (Lint 1)) Eq  (Leaf (Lint 1)))
>         And
>         (Node (Leaf (Var "x")) Gt  (Leaf (Var "y")))
>  , Node (Node (Leaf (Lint 1)) Eq  (Leaf (Var "z")))
>         Or
>         (Node (Leaf (Lint 4)) Gt  (Leaf (Lint 2)))
>  ]

Programa de muestra:

> muestra :: Programa
> muestra = Prog
>           [ Asig "n" (Node (Leaf (Lint 1)) Add (Leaf (Lint 1)))
>           , Print (Leaf (Var "n"))
>           ]

Funciones útiles para BTree

> foldBTree :: (a -> c) -> (c -> b -> c -> c) -> BTree a b -> c
> foldBTree l n (Leaf e)        = l e
> foldBTree l n (Node e1 op e2) = n (foldBTree l n e1) op (foldBTree l n e2)

> instance (Show a, Show b) => Show (BTree a b) where
>   show = foldBTree show (\s1 op s2 -> s1 ++ " " ++ show op ++ " " ++ s2)

Código cochinote para serializar un programa en cadena de caracteres:

> tab :: Int
> tab = 2
>
> espacios :: Int -> String
> espacios n = replicate n ' '
>
> format :: Int -> Comando -> String
> format n (Asig s e)  = espacios n ++ s ++ " = " ++ show e ++ ";"
> format n (If c t e)  = espacios n ++ "if (" ++ show c ++ ") {\n" ++
>                        (case t of
>                          [] -> espacios n ++ "}"
>                          ts -> formatBlq (n+tab) ts ++ "\n" ++
>                                espacios n ++ "}") ++
>                        (case e of
>                         [] -> ""
>                         es -> " else {\n" ++
>                               formatBlq (n+tab) e ++ "\n" ++
>                               espacios n ++ "}")
> format n (While c b) = espacios n ++ "while (" ++ show c ++ ") {\n" ++
>                        (case b of
>                          [] -> espacios n ++ "}"
>                          bs -> formatBlq (n+tab) bs ++ "\n" ++
>                                espacios n ++ "}")
> format n (Print e)   = espacios n ++ "print (" ++ show e ++ ");"
>
> formatBlq :: Int -> Bloque -> String
> formatBlq n []     = ""
> formatBlq n [c]    = format n c
> formatBlq n (c:cs) = format n c ++ "\n" ++ formatBlq n cs

> instance Show Programa where
>     show (Prog blq) = formatBlq 0 blq
>
> instance Show Comando where
>   show = format 0

> instance Show LitVar where
>   show (Lint i)  = show i
>   show (Var v)   = v
>
> instance Show Op where
>     show Add = "+"
>     show Sub = "-"
>     show Mul = "*"
>     show And = "&&"
>     show Or  = "||"
>     show Eq  = "=="
>     show Gt  = ">"

------------------------------------------------------------------------------
Estado de las variables e intérprete de programas

> type Estado = [(String,Integer)]
>
> noInit   :: String -> a
> noInit v = error ("variable '" ++ v ++ "' sin valor")

Intérprete de expresiones:

> evalExp :: Estado -> Exp -> Integer
> evalExp s = foldBTree (evalLeaf s) (\i op d -> evalOp op i d)
>   where evalLeaf            :: Estado -> LitVar -> Integer
>         evalLeaf _ (Lint i) = i
>         evalLeaf s (Var v)  = maybe (noInit v) id (lookup v s)
>         evalOp :: Op -> (Integer -> Integer -> Integer)
>         evalOp Add = (+)
>         evalOp Sub = (-)
>         evalOp Mul = (*)
>         evalOp And = (*)
>         evalOp Or  = (+)
>         evalOp Eq  = \x y -> fromIntegral (fromEnum (x == y))
>         evalOp Gt  = \x y -> fromIntegral (fromEnum (x > y))

Intérprete de programas:

> evalP :: Programa -> IO ()
> evalP (Prog blq) = void $ evalBlq [] blq
>   where
>   evalBlq :: Estado -> Bloque -> IO Estado
>   evalBlq                = foldM evalCom
>   evalCom                :: Estado -> Comando -> IO Estado
>   evalCom s (Asig v e)   = return $ update v (evalExp s e) s
>   evalCom s (If c t e)   = if (evalExp s c) /= 0
>                            then evalBlq s t
>                            else evalBlq s e
>   evalCom s (While c bs) = if (evalExp s c) /= 0
>                            then evalBlq s (bs ++ [While c bs])
>                            else return s
>   evalCom s (Print e)    = do putStrLn $ show $ evalExp s e
>                               return s
>   update                 :: String -> Integer -> Estado -> Estado
>   update v i t           = (v,i) : filter ((/= v) . fst) t

------------------------------------------------------------------------------
Juego de instrucciones del Matarile 69000

> data Instruccion = PUSH Integer
>                  | ADD
>                  | SUB
>                  | MUL
>                  | TEZ
>                  | TNZ
>                  | TGZ
>                    deriving Show
>
> type Codigo  = [Instruccion]


------------------------------------------------------------------------------
Máquina abstracta a pila que ejecuta instrucciones del Matarile 69000

> type Stack = [Integer]
>
> push :: Integer -> Stack -> Stack
> push = (:)
>
> pop :: Stack -> (Integer,Stack)
> pop stk = (head stk, tail stk)

> runExp :: Stack -> Codigo -> Stack
> runExp stk c = foldl runInsts stk c
>   where
>   runInsts :: Stack -> Instruccion -> Stack
>   runInsts stk i = runIns i
>     where
>     (a, stk')  = pop stk
>     (b, stk'') = pop stk'
>     runIns :: Instruccion -> Stack
>     runIns (PUSH i) = push i stk
>     runIns ADD      = push (b + a) stk''
>     runIns SUB      = push (b - a) stk''
>     runIns MUL      = push (b * a) stk''
>     runIns TEZ      = push (fromIntegral $ fromEnum (a == 0)) stk'
>     runIns TNZ      = push (fromIntegral $ fromEnum (a /= 0)) stk'
>     runIns TGZ      = push (fromIntegral $ fromEnum (a > 0))  stk'


