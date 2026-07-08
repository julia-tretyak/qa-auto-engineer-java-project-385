start:
	docker run --rm -p 5173:5173 hexletprojects/qa_auto_java_testing_kanban_board_project_ru_app

start-detached:
	docker run --rm -d -p 5173:5173 --name kanban-app hexletprojects/qa_auto_java_testing_kanban_board_project_ru_app

stop:
	docker stop kanban-app

setup:
	apt-get update -qq && apt-get install -y -qq wget gnupg
	wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
	echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list
	apt-get update -qq && apt-get install -y -qq google-chrome-stable

test:
	./gradlew clean test

report:
	open build/reports/tests/test/index.html
