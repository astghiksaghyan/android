import React, { Component } from 'react';
import DailyStatus from './DailyStatus';
import Login from './Login';
import ParticipationSlider from './ParticipationSlider';
import MenuBar from './MenuBar' ;

import '../styles/App.css';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: this.props.isLoggedIn,
            currentMenuItem: ''
        };
        this.takeCurMenuItem = this.takeCurMenuItem.bind(this);
    }

    takeCurMenuItem(menuItem) {
        this.setState({
            currentMenuItem: menuItem
        });
    }

    render() {
        const divStyle = {
            background: 'url("../assets/back_image.jpg")'
        }
        const myStyle = {
            color: 'red',
            height: '50px'
        }
        let page;
        if (this.state.isLoggedIn) {
            page = <div style={divStyle}>
                        <MenuBar handlerFromParant={this.takeCurMenuItem} />
                        <div>
                            <DailyStatus />
                            <ParticipationSlider />
                    	</div>
                </div>;
        } else {
            page = <Login />
        }
    return (
      <div className="App">
          {page}
          <p style={myStyle} >{this.state.currentMenuItem}</p>
      </div>
    );
  }
}

export default App;
