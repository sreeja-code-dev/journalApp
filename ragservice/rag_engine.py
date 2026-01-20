import chromadb
from sentence_transformers import SentenceTransformer

client = chromadb.Client()
collection = client.create_collection("journal")

model = SentenceTransformer("all-MiniLM-L6-v2")

# Add entry
text = "Feeling overwhelmed before finals."
embedding = model.encode(text).tolist()
collection.add(documents=[text], embeddings=[embedding], ids=["1"])

# Query similar entries
query = "Exam stress"
q_embedding = model.encode(query).tolist()
results = collection.query(query_embeddings=[q_embedding], n_results=3)
print(results)
