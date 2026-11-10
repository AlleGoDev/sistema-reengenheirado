# Relatório — Pipeline de evolução segura (CI/CD)

Disciplina: Dinâmica e Evolução de Software  
Entrega: esteira GitHub Actions com SonarCloud, Trivy e Docker Hub

## 1. Repositório GitHub

Link (público, com a aba Actions visível):

> https://github.com/SEU_USUARIO/SEU_REPOSITORIO

## 2. Imagem no Docker Hub

Link da tag publicada pelo workflow (exemplo `v1.0.0`):

> https://hub.docker.com/r/SEU_USUARIO/sistema-reengenheirado/tags

## 3. Evidência do SonarCloud

Captura do dashboard com três itens visíveis:

- status do Quality Gate (*Passed*)
- Code Smells e duplicações (overview do projeto)
- complexidade ciclomática em *Measures → Complexity → Complexity*

Incluir a imagem aqui ao montar o PDF.

O que o workflow exige para essa tela existir: secret `SONAR_TOKEN`, variables `SONAR_ORGANIZATION` e `SONAR_PROJECT_KEY`, repositório importado no SonarCloud com *Automatic Analysis* desligada, e um push da tag `v*`. O scanner espera o Quality Gate (`sonar.qualitygate.wait=true`). Se o gate falha, o job do Docker Hub não começa.

## 4. Evidência do Trivy

Captura do log do GitHub Actions, no step **Trivy na imagem Docker** ou **Rodar Trivy Vulnerability Scanner**, mostrando a tabela de vulnerabilidades (severidades CRITICAL e HIGH).

Incluir a imagem aqui ao montar o PDF.

O scan do repositório bloqueia a esteira quando há CVE CRITICAL ou HIGH com correção já publicada (`exit-code: 1`, `ignore-unfixed: true`). A imagem só é enviada ao Docker Hub depois de um segundo scan, feito sobre a imagem construída e ainda não publicada. Esse segundo scan é o que enxerga vulnerabilidades do JDK e do Alpine.

## 5. Ensaio crítico

A Lei da Complexidade Crescente de Lehman diz que um sistema usado e evoluído fica mais complexo, a menos que se faça trabalho contínuo para conter essa complexidade. Nas primeiras aulas isso apareceu como entropia de software e débito técnico: o processador de vendas misturava validação, imposto, frete e impressão do recibo, com números mágicos e nomes que não revelavam a regra de negócio. A reengenharia — responsabilidades separadas e Strategy para frete e imposto — reduziu essa entropia uma vez. Não a impede de voltar. Cada entrega manual, feita só porque o código compilou, reabre a porta para o monólito espaguete: o custo da degradação fica invisível até a próxima crise. A esteira inverte o incentivo. Uma versão só existe em produção (a tag no Docker Hub) se passar por um controle automático. O gatilho em tag, e não em todo commit, separa o trabalho do dia a dia da decisão explícita de lançar.

O SonarCloud e o Trivy pagam, a cada lançamento, o juro de dois débitos diferentes. O Sonar torna observáveis as métricas que a revisão humana esquece quando o prazo aperta: complexidade ciclomática, code smells e duplicação. Com o Quality Gate à espera do resultado, um aumento relevante de complexidade ou um smell que derrube a nota de manutenibilidade falha o job e corta o `needs` do job seguinte. O código reengenheirado deixa de ser um estado e passa a ser uma invariante da release. O Trivy cobre o que o Sonar de desenho não cobre: o débito de segurança em bibliotecas e na imagem base. Uma CVE crítica no OpenJDK ou no Alpine não precisa ser descoberta em produção; a tabela aparece no log e, quando existe correção, o push não acontece. Juntos, os dois gates não revogam a lei de Lehman — o sistema continua a crescer — mas convertem entropia invisível em sinal bloqueante. A evolução deixa de depender da memória da equipe e passa a ser um ciclo em que cada versão etiquetada só segue adiante se a complexidade e as vulnerabilidades tiverem sido medidas.
