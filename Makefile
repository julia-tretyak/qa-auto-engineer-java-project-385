start:
	docker run --rm -p 5173:5173 hexletprojects/qa_auto_java_testing_kanban_board_project_ru_app

start-detached:
	docker run --rm -d -p 5173:5173 --name kanban-app hexletprojects/qa_auto_java_testing_kanban_board_project_ru_app

stop:
	docker stop kanban-app

test:
	./gradlew clean test

report:
	open build/reports/tests/test/index.html
