#!/bin/bash
# Script de compilacao do projeto GraphTasksTDEs.

JAVAC="javac"
JAVA_FILES=$(find src -name "*.java")

echo "=== Compilando projeto GraphTasksTDEs ==="

# Gera bytecode compativel com Java 8, conforme documentacao do projeto.
$JAVAC --release 8 -d output -sourcepath src $JAVA_FILES

if [ $? -eq 0 ]; then
    echo "Compilacao bem-sucedida!"
    echo ""
    echo "Para executar o menu interativo:"
    echo "  java -cp output br.edu.grafo.app.Main"
    echo ""
    echo "Para executar o exemplo:"
    echo "  java -cp output br.edu.grafo.app.ExampleGraph"
    echo ""
    echo "Para executar a interface grafica:"
    echo "  java -cp output br.edu.grafo.app.GraphDesktopApp"
else
    echo "Erro na compilacao"
    exit 1
fi
