import openai

# Set your API key (you can also use environment variables)
openai.api_key = "your-openai-api-key"

def generate_reflection(text, similar_entries):
    prompt = f"""
    You are a journaling assistant.
    Here is a new entry: "{text}"
    Here are similar past entries:
    {similar_entries}

    Reflect on patterns and offer supportive insights.
    """

    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": prompt}]
    )
    return response['choices'][0]['message']['content']
