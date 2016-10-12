import GuarriC

fact6 :: Programa
fact6 = Prog
		[Asig "n" (Leaf (Lint 6)),
		Asig "r" (Leaf (Lint 1)),
		While (Node (Leaf (Var "n")) Gt (Leaf (Lint 0))) 
		[Asig "r" (Node (Leaf (Var "r")) Mul (Leaf (Var "n"))),
		Asig "n" (Node (Leaf (Var "n")) Sub (Leaf (Lint 1)))],
		Print (Leaf (Var "r"))]

	
notFour :: Programa
notFour = Prog
		[Asig "n" (Leaf (Lint 10)),
		While (Node (Leaf (Var "n")) Gt (Leaf (Lint 0))) 
		[If (Node (Leaf (Var "n")) Eq (Leaf (Lint 4)))
		[Print (Leaf (Var "-4"))]
		[Print (Leaf (Var "n"))],
		Asig "n" (Node (Leaf (Var "n")) Sub (Leaf (Lint 2)))]]
		

getVars :: Exp -> [String]
getVars (Leaf (Var a)) = [a]
getVars (Leaf (Lint b)) = [] 
getVars (Node s1 op s2) = (getVars s1) ++ (getVars s2)