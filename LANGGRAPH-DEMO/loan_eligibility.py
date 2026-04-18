from langgraph.graph import StateGraph, START , END
from typing import TypedDict

class LoanState(TypedDict):
    income: float
    age: int
    eligible: bool
    max_loan_amount: float
    category: str

def calc_eligibility(state: LoanState) -> LoanState:
    income= state['income']
    age= state['age']

    eligible= income >= 30000 and 21 <= age <= 65
    max_loan_amount= income * 5 if eligible else 0
    state['eligible']= eligible
    state['max_loan_amount']= max_loan_amount
    return state

def label_category(state: LoanState) -> LoanState:

    income= state['income']
    eligible= state['eligible']

    if not eligible:
        state['category']= 'Not Eligible'
    elif income < 50000:
        state['category']= 'Low Income'     
    elif income < 100000:
        state['category']= 'Medium Income'
    return state

graph= StateGraph(LoanState)
graph.add_node('calc_eligibility', calc_eligibility)
graph.add_edge(START, 'calc_eligibility')
graph.add_node('label_category', label_category)
graph.add_edge('calc_eligibility', 'label_category')
graph.add_edge('label_category', END)

workflow= graph.compile()

initials_state: LoanState= {
    'income': 45000,
    'age': 30
}
final_state=workflow.invoke(initials_state)
print(final_state)


png = workflow.get_graph().draw_mermaid_png()
with open('llm_workflow.png', 'wb') as f:
    f.write(png)