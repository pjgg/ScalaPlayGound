# Mealy state machine

q => states: Tender, Person
q'=> next state: tender, Person
e => input message: FullPint, EmptyPint, Ticket
s => output message: FullPint, EmptyPint, Ticket, None
Init state => Person
Final State => Tender

## Transitions table 

| q             | q'                                | s                                       |
| ------------- |-----------------------------------|----------------------------------------:|
|               | FullPint    EmptyPint    Ticket   |FullPint     EmptyPint     Ticket        |
| Tender        | Tender  |Tender       | Person    | None    | None        | FullPint        |
| Person        | Tender  |Person       | Person    | Tender  | None        | FullPint        |
