from flask import Flask, request, jsonify
import chromadb
from sentence_transformers import SentenceTransformer
from genai import generate_reflection  #   GenAI module

app = Flask(__name__)
client = chromadb.Client()
collection = client.create_collection("journal")
model = SentenceTransformer("all-MiniLM-L6-v2")

@app.route("/embed", methods=["POST"])
def embed_and_query():
    try:
        data = request.get_json(force=True)
        text = data["text"]
        entry_id = data["id"]
        query = data["query"]

        # Embed and store the journal entry
        embedding = model.encode(text).tolist()
        collection.add(documents=[text], embeddings=[embedding], ids=[entry_id])

        # Embed and query similar entries
        q_embedding = model.encode(query).tolist()
        results = collection.query(query_embeddings=[q_embedding], n_results=3)

        # Prepare similar entries for GenAI
        similar_entries = "\n".join(results["documents"][0])
        reflection = generate_reflection(text, similar_entries)

        return jsonify({
            "reflection": reflection,
            "similar_entries": results["documents"][0],
            "distances": results["distances"][0]
        })

    except Exception as e:
        print("Error:", e)
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=5000)
