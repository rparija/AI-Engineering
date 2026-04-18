from langgraph.graph import StateGraph, START , END
from typing import TypedDict
from langchain_openai import ChatOpenAI
from dotenv import load_dotenv

load_dotenv()

model= ChatOpenAI(model_name='gpt-4o')

class JokeState(TypedDict):
    topic: str
    joke: str
    rating: str   

def startup_comedian(state: JokeState) -> JokeState:
    prompt = f"Tell me a funny joke about {state['topic']}. Output format: <joke>"
    joke= model.invoke(prompt)
    state['joke']= joke.content
    return state

def rate_joke(state: JokeState) ->JokeState:
    prompt= f"On a scale of 1 to 10, how funny is this joke: {state['joke']}? Ouput format: <number>"
    rating_str= model.invoke(prompt)
    state['rating']= rating_str.content
    return state

graph = StateGraph(JokeState)

graph.add_node('startup_comedian',startup_comedian)
graph.add_edge(START, 'startup_comedian')
graph.add_node('rate_joke', rate_joke)
graph.add_edge('startup_comedian', 'rate_joke')
graph.add_edge('rate_joke', END)        

workflow = graph.compile()

initial_state = {
    "topic": "startups"
}

final_state = workflow.invoke(initial_state)
print(final_state)

png = workflow.get_graph().draw_mermaid_png()
with open('llm_workflow.png', 'wb') as f:
    f.write(png)