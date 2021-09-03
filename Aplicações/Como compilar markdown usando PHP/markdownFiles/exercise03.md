# Observe o algorítmo

```python
def misteriousAlgorithm(arr):
    n = len(arr)
  	for i in range(n-1):
        for j in range(0, n-i-1):
          if arr[j] > arr[j + 1] :
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
  
arr = [64, 34, 25, 12, 22, 11, 90]
```
--- 

Descreva em detalhes o que esse algorítmo faz e qual é o seu nome.

