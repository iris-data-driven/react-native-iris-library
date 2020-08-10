import React, { Component } from 'react';
import {
    View,
    Text, 
    StyleSheet,
    SafeAreaView,
    FlatList,
    ActivityIndicator,
    Alert
} from 'react-native';
import IrisModule from './IrisModule';
import Colors from '../constants/colors';
import CloseButton from '../components/CloseButton';
import TrashButton from '../components/TrashButton';

class NotificationCenter extends Component {
    state = {
        listViewData: []
    };
    getNotifications = async () => {
        try {
            IrisModule.getNotificationList( notificationList => {
                this.setState({
                    listViewData: notificationList
                });
            });
        } catch(e) {}
        
    };
    removeAllNotifications = () => {
        console.log('DELETE');
        IrisModule.deleteAllNotifications();
        this.props.onCreateClicked(false);
    };
    updateNotification = (notification, index) => {

    };
    deleteNotification = (notification, index) => {

    };
    
    async componentDidMount(){
        this.displaySpinner();
        this.getNotifications();
        
    }
    displaySpinner(){
        return(
          <View style={[styles.container, styles.horizontal]}>
            <ActivityIndicator size="large" color={Colors.orangeIris} />
          </View>
        )
      }
      removeAllNotificationsAlert = () =>
        Alert.alert(
        "Atenção",
        "Quer apagar todas as notificações?",
        [
            { text: "OK", onPress: () => this.removeAllNotifications() }
        ],
        { cancelable: false }
        );

    render() {
        const {listViewData} = this.state;   
        const Item = ({title, subtitle, body}) => (
            <View style={styles.item}>
                <Text style={styles.title}>{title}</Text>
                <Text style={styles.subtitle}>{subtitle}</Text>
                <Text numberOfLines={2} style={styles.body}>{body}</Text>
            </View>
        );
        
        const renderItem = ({item}) => (
            <Item title={item.title} subtitle={item.subtitle} body={item.body}/>
        );
        return(
            <SafeAreaView style={{flex: 1}}>
                <View style={{flexDirection: 'row', alignContent: 'center', justifyContent: 'center'}}>
                        <TrashButton onPress={() => {this.removeAllNotificationsAlert();}} />
                    <View style={{ justifyContent: 'center'}}>
                        <Text style={{padding: 10, fontSize: 24}}>Notificações</Text>
                    </View>
                        <CloseButton onPress={() => {this.props.onCreateClicked(false);}} />
                </View>
                <FlatList
                        contentContainerStyle={{ flexGrow: 1 }}
                        extraData={this.state}
                        data={this.state.listViewData}
                        renderItem={renderItem}
                        keyExtractor={(item, index) => index.toString()} 
                    />
            </SafeAreaView>
        );
    }
}
const styles = StyleSheet.create({
    title: {
        color: Colors.blackIris,
        fontSize: 16,
        fontWeight: 'bold',
        margin: 5
    },
    subtitle: {
        color: Colors.lightGrayIris,
        marginHorizontal: 5
    },
    body: {
        color: Colors.lightGrayIris,    
        margin: 5
    },
    container: {
        flex: 1,
        alignItems: "center",
        backgroundColor: "grey",
        marginVertical: 20,
        height: 30,
        flexDirection: 'column',
        justifyContent: 'space-between'
      },
    item: {
        padding: 5,
        marginVertical: 10,
        marginHorizontal: 16,
        elevation:4,
        shadowOffset: { width: 5, height: 5 },
        shadowColor: "grey",
        shadowOpacity: 0.5,
        shadowRadius: 10,
        backgroundColor: '#ffffff',
        borderRadius: 10
      },
    separatorLine: {
        marginVertical: 10,
        backgroundColor: Colors.lightGrayIris,
        height: 1,
      },
});


export default NotificationCenter;  