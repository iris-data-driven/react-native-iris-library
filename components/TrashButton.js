import React from 'react';
import {
    StyleSheet,
    TouchableOpacity, 
    Image,
    View
} from 'react-native';

const TrashButton = ({onPress}) => {
    return <View style={styles.container}>
                <TouchableOpacity onPress={onPress} style={styles.closeButton}>
                    <Image style={styles.image} resizeMode={"contain"} source={require('../assets/images/trash.png')} />
                </TouchableOpacity>
            </View>
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'flex-start',
        margin: 10,
        paddingTop: 10
      },
    closeButton: {
        flexDirection: 'column',
        alignItems: 'flex-start',
        height: 40,
        width: 40,
        borderRadius: 12.5,
        margin: 5,
    },
    image: {
        padding: 10,
        margin: 7.5,
        height: 25,
        width: 25,
        resizeMode: 'stretch',
    }
});
export default TrashButton;