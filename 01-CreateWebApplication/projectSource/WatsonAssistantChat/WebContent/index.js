class App extends React.Component {
    constructor() {
        super()
        this.state = {
            messages: []
        }
        this.sendMessage = this.sendMessage.bind(this)
    } 
    
//    componentDidMount() {
//    }

    sendMessage(text) {
    	//fetch('http://localhost:9080/WatsonAssistantChat/chatbot/chatservice/?conversationMsg=' + text + '&conversationCtx={"usr":"ezamora"}#')
    	fetch('https://watsonassistantchatezamora.mybluemix.net/chatbot/chatservice/?conversationMsg=' + text + '&conversationCtx={"usr":"ezamora"}#')
        .then((response) => {
          return response.json()
        })
        .then((myJsonResponse) => {
        	this.setState({
	            messages: [...this.state.messages, myJsonResponse]
	        })
        })
    }
    
    render() {
        return (
            <div className="app">
              <Title />
              <MessageList
                  messages={this.state.messages} />
              <SendMessageForm
                  sendMessage={this.sendMessage} />
            </div>
        );
    }
}

class MessageList extends React.Component {
    render() {
        return (
            <ul className="message-list">
                {this.props.messages.map((message, index) => {
                    return (
                      <li  key={message.response} className="message">
                      	<div>{message.context.ctx.usr}</div>  
                      	<div>{message.context.msg}</div>
                      </li>
                    )
                })}
            </ul>
        )
    }
}

class SendMessageForm extends React.Component {
    constructor() {
        super()
        this.state = {
            message: ''
        }
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }
    
    handleChange(e) {
        this.setState({
            message: e.target.value
        })
    }
    
    handleSubmit(e) {
        e.preventDefault()
        this.props.sendMessage(this.state.message)
        this.setState({
            message: ''
        })
    }
    
    render() {
        return (
            <form
                onSubmit={this.handleSubmit}
                className="send-message-form">
                <input
                    onChange={this.handleChange}
                    value={this.state.message}
                    placeholder="Type your message and hit ENTER"
                    type="text" />
            </form>
        )
    }
}

function Title() {
  return <p className="title">Simple chat app (For IBM cloud deploy)</p>
}

ReactDOM.render(<App />, document.getElementById('root'));