import pendulum

from airflow import DAG
from airflow.decorators import task
from spark import Batching_Jobs

with DAG(
        dag_id="simple_workflow_dag",
        schedule=None,
        start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
        catchup=False,
        tags=["test"],
) as dag:
    base_date = pendulum.date(2013, 1, 1)
    iteration = 5

    @task(task_id="first_job")
    def first(date: str) -> str:
        print("this is the first step :" + date)
        return second(date)

    @task(task_id="second_job")
    def second(src: str):
        print("this is the second step :" + src)
        third()

    @task(task_id="third_job")
    def third():
        print("this is the third step :")

    first(base_date)