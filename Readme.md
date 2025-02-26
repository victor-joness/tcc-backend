# Documentação dos Endpoints

## 1. **WordController**

### 1.1. **Criar uma nova palavra**
- **Método:** POST
- **Endpoint:** `/words`
- **Descrição:** Cria uma nova palavra associada a uma categoria existente.
- **Corpo da Requisição (JSON):**
  ```json
  {
    "word": "string",
    "video": "string",
    "status": "string",
    "modulo": "string",
    "description": "string",
    "categoryId": "long"
  }