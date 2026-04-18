from langgraph.graph import StateGraph, START , END
from typing import Annotated, TypedDict
from langchain_openai import ChatOpenAI
from dotenv import load_dotenv
from pydantic import BaseModel, Field
import operator

load_dotenv()

model= ChatOpenAI(model_name='gpt-4o')

class HealthSchema(BaseModel):
    metric_name: str = Field(..., description="Name of the health metric")
    value: float = Field(..., description="Value of the health metric")
    explanation: str = Field(..., description="Explanation of the health metric")

structured_model = model.with_structured_output(HealthSchema)

class HealthState(TypedDict):
    age: int
    weight: float
    height: float
    activity_level: str
    bmi_feedback: str
    bmr_feedback: str
    calorie_feedback: str
    overall_feedback: str
    individual_values: Annotated[list[float], operator.add]

def calculate_bmi(state: HealthState) -> HealthState:
        prompt = f"Calculate the BMI for a person with weight {state['weight']} kg and height {state['height']} cm. Provide feedback."
        feedback = structured_model.invoke(prompt)
        return {'bmi_feedback': feedback.explanation, 'individual_values': [feedback.value]}
    
def calculate_bmr(state: HealthState) -> HealthState:
        prompt = f"Calculate the BMR for a person aged {state['age']} years, weight {state['weight']} kg, and height {state['height']} cm. Provide feedback on the BMR value.."
        feedback = structured_model.invoke(prompt)
        return {'bmr_feedback': feedback.explanation, 'individual_values': [feedback.value]}
    
def calculate_calorie_needs(state: HealthState) -> HealthState:
        weight = state['weight']
        height = state['height']
        age = state['age']
        prompt = f"Calculate the daily calorie needs for a person with activity level {state['activity_level']}, weight {weight} kg, height {height} cm, and age {age}. Provide feedback on the calorie needs.."
        feedback = structured_model.invoke(prompt)
        return {'calorie_feedback': feedback.explanation, 'individual_values': [feedback.value]}
    
def generate_report(state: HealthState) -> HealthState:
        bmi_feedback = state['bmi_feedback']
        bmr_feedback = state['bmr_feedback']
        calorie_feedback = state['calorie_feedback']
        prompt = f"Generate a comprehensive health report based on the following feedbacks:\nBMI Feedback: {bmi_feedback}\nBMR Feedback: {bmr_feedback}\nCalories Feedback: {calorie_feedback}"
        report = structured_model.invoke(prompt)
        return {'overall_report': report}
    
graph = StateGraph(HealthState)
graph.add_node('calculate_bmi', calculate_bmi)
graph.add_node('calculate_bmr', calculate_bmr)
graph.add_node('calculate_calorie_needs', calculate_calorie_needs)
graph.add_node('generate_report', generate_report)
graph.add_edge(START, 'calculate_bmi')
graph.add_edge(START, 'calculate_bmr')
graph.add_edge(START, 'calculate_calorie_needs')
graph.add_edge('calculate_bmi', 'generate_report')
graph.add_edge('calculate_bmr', 'generate_report')
graph.add_edge('calculate_calorie_needs', 'generate_report')

graph.add_edge('generate_report', END)

workflow = graph.compile()

initial_state = {
   'age': 34,
    'weight': 78.0,
    'height': 173.0,
    'activity_level': 'moderate'
}

final_state = workflow.invoke(initial_state)
print(final_state)

png = workflow.get_graph().draw_mermaid_png()
with open('health_report.png', 'wb') as f:
    f.write(png)